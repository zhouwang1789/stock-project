from django.http import Http404
from django.shortcuts import render

from .models import DailyAdjusted, Stock


def home(request):
    Stock.objects.update_or_create(symbol="msft")
    stocks = Stock.objects.all()
    return render(request, 'home.html', {
        'stocks': stocks,
    })


def daily_adjusted(request, symbol):
    try:
        das = DailyAdjusted.objects.all()
    except DailyAdjusted.DoesNotExist:
        raise Http404('DailyAdusted not found')
    return render(request, 'daily_adjusted.html', {
        'das': das,
    })
