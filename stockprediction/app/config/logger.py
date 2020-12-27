import logging
import os
import sys
from logging.handlers import RotatingFileHandler

log_dir = "/var/log/com/zwang/stock/"
if not os.path.exists(log_dir):
    os.makedirs(log_dir)

log_file = log_dir + "prediction.log"
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(threadName)-12.12s] [%(levelname)-5.5s] %(message)s",
    handlers=[
        logging.StreamHandler(sys.stdout),
        RotatingFileHandler(log_file, maxBytes=(1048576 * 10), backupCount=7)
    ])

log = logging.getLogger()
