# -*- coding: utf-8 -*-
# try something like
def index(): 
    return list()

def list():
    if auth.is_logged_in():
        posts=db(db.solvers.cor_user==auth.user.id).select()
        return dict(posts=posts)
    else:
        return dict()
