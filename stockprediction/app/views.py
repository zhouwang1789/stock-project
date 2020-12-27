from datetime import date, timedelta

import pandas as pd
import requests
from django.http import Http404
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

from app.config.logger import log
from .learner.pred_decision_tree import DecisionTreeLearner
from .models import DailyAdjusted


def home(request):
    return render(request, 'home.html', {})


@csrf_exempt
def stock(request):
    symbol, data_size = "", 0
    if request.method == "POST":
        symbol = request.POST.get("stock_symbol", "")
        data_size = int(request.POST.get("stock_data_size", ""))
    if len(symbol) == 0:
        return render(request, 'home.html', {"error": f'Failed to get stock symbol'})

    log.info(f'Received symbol: {symbol}, data size: {data_size}')
    try:
        das = DailyAdjusted.objects.filter(symbol=symbol).order_by("dateTime").values("adjustedClose", "dateTime")
    except DailyAdjusted.DoesNotExist:
        raise Http404(f'DailyAdjusted not found for {symbol}')

    if not das or das.last()["dateTime"].date() != (date.today() - timedelta(days=1)):
        response = requests.get(f'http://stock-data-integration:8080/stock/dailyAdjusted/{symbol}')
        if response.ok:
            DailyAdjusted.objects.filter(symbol=symbol).delete()  # stock prices change often, so delete the old data
            save(response.json(), symbol)  # save the new data
        else:
            return render(request, 'stock.html', {
                "stock_symbol": symbol,
                "error": f'Failed to get stock data for symbol:[{symbol}]'
            })

    df = pd.DataFrame.from_records(DailyAdjusted.objects.filter(symbol=symbol).values())
    df = df[len(df) - data_size:]
    prediction_res = DecisionTreeLearner.predict(df)

    return render(request, 'stock.html', {
        "stock_symbol": symbol,
        'prediction_res': prediction_res,
    })


def save(data, symbol):
    new_das = []
    if len(data) > 0:
        for d in data:
            da = DailyAdjusted(**d)
            da.symbol = symbol
            new_das.append(da)
        DailyAdjusted.objects.bulk_create(new_das)
