import traceback
from statistics import mean

from pandas import DataFrame
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier

from app.config.const import *
from app.config.logger import log
from app.util import indicators


class DecisionTreeLearner:
    """
    Predict stock price based on historical data using decision tree algorithm from scikit learn library
    """

    @staticmethod
    def predict(df: DataFrame):
        # while self.db.check_flag(technical_indicator_prediction):
        try:
            if len(df) < MIN_DF_LENGTH:
                return {"Error": f'Data size[{len(df)}] is less than the minimum[{MIN_DF_LENGTH}]'}

            res = dict()
            log.info(f'Received original df size:[{len(df)}]')
            df_copy = df.copy()
            df_copy = df_copy['adjustedClose'].to_frame()
            df_copy = indicators.get_sma(df_copy, 5)
            df_copy = df_copy[4:]
            df_copy['trend'] = df_copy['adjustedClose'].diff().gt(0).astype(float)
            x = df_copy.values[:, 0:1]
            y = df_copy.values[:, -1].astype('int')
            x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.3, random_state=42)

            # decision tree - gini
            dr_gini = DecisionTreeClassifier(criterion="gini", random_state=42, max_depth=5, min_samples_leaf=5)
            dr_gini.fit(x_train, y_train)
            y_pred_dr_gini = dr_gini.predict(x_test)
            log.info(f'Decision tree gini accuracy: {accuracy_score(y_test, y_pred_dr_gini):.4}')

            # decision tree - entropy
            dr_entropy = DecisionTreeClassifier(criterion="entropy", random_state=42, max_depth=3,
                                                min_samples_leaf=5)
            dr_entropy.fit(x_train, y_train)
            y_pred_dr_entropy = dr_entropy.predict(x_test)
            log.info(f'decision tree entropy accuracy: {accuracy_score(y_test, y_pred_dr_entropy):.4}')

            # random forrest - gini
            rf_gini = RandomForestClassifier(n_estimators=100, criterion="gini", random_state=42)
            rf_gini.fit(x_train, y_train)
            y_pred_rf_gini = rf_gini.predict(x_test)
            log.info(f'random forest gini accuracy: {accuracy_score(y_test, y_pred_rf_gini):.4}')

            # decision tree - entropy
            rf_entropy = RandomForestClassifier(n_estimators=100, criterion="entropy", random_state=42)
            rf_entropy.fit(x_train, y_train)
            y_pred_rf_entropy = rf_gini.predict(x_test)
            log.info(f'random forest entropy accuracy: {accuracy_score(y_test, y_pred_rf_entropy):.4}')
            log.info(f'Completed decision tree prediction')

            accuracy = mean([accuracy_score(y_test, y_pred_dr_gini),
                             accuracy_score(y_test, y_pred_dr_entropy),
                             accuracy_score(y_test, y_pred_dr_gini),
                             accuracy_score(y_test, y_pred_rf_entropy)])
            return {"Decision tree accuracy": f'{accuracy:.4}', "Actual data size": f'{len(df_copy)}'}
        except:
            log.error(traceback.format_exc())
