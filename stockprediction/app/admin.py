from django.contrib import admin

from .models import DailyAdjusted


@admin.register(DailyAdjusted)
class DailyAdjustedAdmin(admin.ModelAdmin):
    list_display = ['dateTime', 'adjustedClose']
