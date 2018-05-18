# -*- encoding=UTF-8 -*-

from flask import Flask

from flask_sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.jinja_env.add_extension('jinja2.ext.loopcontrols')
app.config.from_pyfile('app.conf')
db = SQLAlchemy(app)

from nowstagram import views, models
