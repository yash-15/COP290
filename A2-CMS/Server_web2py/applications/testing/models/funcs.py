# -*- coding: utf-8 -*-

def authe_admin(aid):
    admin=db((db.administrators.id==aid)&(db.administrators.cor_user==auth.user.id)).select()
    return (len(admin)>0)
