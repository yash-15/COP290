# -*- coding: utf-8 -*-
# this file is released under public domain and you can use without limitations

#########################################################################
## This is a sample controller
## - index is the default action of any application
## - user is required for authentication and authorization
## - download is for downloading files uploaded in the db (does streaming)
#########################################################################
import os
def index():
    """
    example action using the internationalization operator T and flash
    rendered by views/default/index.html or views/generic.html

    if you need a simple wiki simply replace the two lines below with:
    return auth.wiki()
    """
    response.flash = T("Hello World")
    return dict(message=T('Welcome to Complaint Management System!'))


def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()

@request.restful()
def api():
    response.view = 'generic.'+request.extension
    def GET(*args,**vars):
        patterns = 'auto'
        parser = db.parse_as_rest(patterns,args,vars)
        if parser.status == 200:
            return dict(content=parser.response)
        else:
            raise HTTP(parser.status,parser.error)
    def POST(table_name,**vars):
        return db[table_name].validate_and_insert(**vars)
    def PUT(table_name,record_id,**vars):
        return db(db[table_name]._id==record_id).update(**vars)
    def DELETE(table_name,record_id):
        return db(db[table_name]._id==record_id).delete()
    return dict(GET=GET, POST=POST, PUT=PUT, DELETE=DELETE)

def login():
    userid = request.vars.userid
    password = request.vars.password
    user = auth.login_bare(userid,password)
    try:
        file_pic=open(os.getcwd()+"\\applications\\testing\\uploads\\"+user.profile_pic,'rb')
        pic1=bytearray(file_pic.read())
    except Exception,e:
        pic1=[]
    pic=[]
    for i in range(0,len(pic1)):
        pic.append(pic1[i])
    return dict(success=False if not user else True, user=user,pic=pic)

def logout():
    return dict(success=True, loggedout=auth.logout())
