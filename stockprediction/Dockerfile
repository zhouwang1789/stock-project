FROM python:3.7.9

# set work directory
WORKDIR /usr/src/app

# set environment variables
ENV PYTHONDONTWRITEBYTECODE 1
ENV PYTHONUNBUFFERED 1

RUN pip install --upgrade pip
COPY ./requirements.txt .
RUN pip install -r requirements.txt

# copy project
COPY ./stockprediction/ ./stockprediction
COPY ./app/ ./app
COPY ./manage.py .

# initialize sqlite database
RUN python3 manage.py makemigrations
RUN python3 manage.py migrate