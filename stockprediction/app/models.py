from django.db import models


class DailyAdjusted(models.Model):
    symbol = models.CharField(max_length=10)
    dateTime = models.DateTimeField()
    open = models.DecimalField(max_digits=19, decimal_places=10)
    high = models.DecimalField(max_digits=19, decimal_places=10)
    low = models.DecimalField(max_digits=19, decimal_places=10)
    close = models.DecimalField(max_digits=19, decimal_places=10)
    adjustedClose = models.DecimalField(max_digits=19, decimal_places=10)
    volume = models.IntegerField(null=True)
    dividendAmount = models.DecimalField(max_digits=19, decimal_places=10)
    splitCoefficient = models.DecimalField(max_digits=19, decimal_places=10)


class Stock(models.Model):
    symbol = models.CharField(max_length=10)
