from pandas import DataFrame


def get_momentum(df: DataFrame, window: int):
    return df / df.shift(window) - 1


def get_sma(df: DataFrame, window: int):
    return df.rolling(window=window).mean()


def get_bollinger(df: DataFrame, window: int):
    avg = df.rolling(window=window).mean()
    std = df.rolling(window=window).std()
    upper_band = avg + 2 * std
    lower_band = avg - 2 * std
    return avg, upper_band, lower_band


def get_daily_return(df: DataFrame):
    return df / df.shift(1) - 1
