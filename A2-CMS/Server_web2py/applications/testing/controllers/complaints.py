# -*- coding: utf-8 -*-
# try something like
from datetime import datetime,timedelta

def index(): return dict(message="hello from complaints.py")

@auth.requires_login()
def normview():
    l=len(request.args)
    if l==0:
        raise HTTP(404)
    else:
        comp_type=str(request.args[0]).lower()
        if (l==1):
            if (comp_type=="ind"):
                comp=db((db.ind_complaint.User_ID==auth.user.id)&(db.ind_complaint.Admin_ID==None)).select()
            elif (comp_type=="hstl"):
                comp=db(db.grp_complaint.locality==auth.user.locality).select()
            elif (comp_type=="insti"):
                comp=db(db.grp_complaint.locality==17).select()
            else:
                raise HTTP(404)
        elif (l==2):
            cid=int(request.args[1])
            if (comp_type=="ind"):
                comp=db((db.ind_complaint.User_ID==auth.user.id)&(db.ind_complaint.id==cid)).select()
            elif (comp_type=="hstl" or comp_type=="insti"):
                comp=db(db.grp_complaint.locality==auth.user.locality and db.grp_complaint.id==cid).select()
            else:
                raise HTTP(404)
            if (len(comp)>0):
                comp=comp.first()
            else:
                raise HTTP(404)
        else:
            raise HTTP(404)
    return dict(complaints=comp)

@auth.requires_login()
def solverview():
    l=len(request.args)
    if (l==0):
        raise HTTP(404)
    else:
        sid=int(request.args[0])
        cor_user_id=db(db.solvers.id==sid).select()
        if (len(cor_user_id)>0):
            cor_user_id=cor_user_id.first()
            if not(cor_user_id.cor_user==auth.user.id):
                raise HTTP(404)
        else:
            raise HTTP(404)
            
        ## Passed the authentication test
        if (l==1):
            comp=db(db.ind_complaint.Solver_ID==sid).select()
        elif (l==2):
            cid=int(request.args[1])
            comp=db((db.ind_complaint.id==cid)&(db.ind_complaint.Solver_ID==sid)).select()
            if(len(comp)>0):
                comp=comp.first()
            else:
                raise HTTP(404)
        else:
            raise HTTP (404)
    return dict(complaints=comp)

def isparent(id1,id2):
    # Tells if id1 is a parent of id2
    temp=db(db.administrators.id==id2).select()
    try:
        temp=temp.first()
    except Exception,e:
        raise e
    if (temp.parent==None):
        return False
    elif (temp.parent==id1):
        return True
    else:
        return isparent(id1,temp.parent)

@auth.requires_login()
def adminview():
    # Can view the complaints which are/were addressed to him/her or to his/her child-admins.
    #Currently not dealing with children
    # Categories- Admin_Individual Complaints, Pending Complaints, logs
    # Logs is the history of group level complaints
    
    l=len(request.args)
    if (l<2):
        raise HTTP(404)
    else:
        aid=int(request.args[0])
        cor_user_id=db(db.administrators.id==aid).select()
        if (len(cor_user_id)>0):
            cor_user_id=cor_user_id.first()
            if not(cor_user_id.cor_user==auth.user.id):
                raise HTTP(404)
        else:
            raise HTTP(404)
        
        ## Passed the authentication test (aid corresponds to the logged user)
        
        comp_type=str(request.args[1])
        if (l==2):
            if (comp_type=="admin_ind"):
                comp=db((db.ind_complaint.User_ID==auth.user.id)&(db.ind_complaint.Admin_ID==aid)).select()
            elif (comp_type=="pending"):
                comp=db((db.grp_complaint.Cur_Admin_ID==aid)&(db.grp_complaint.Status in [2,3,6])).select()
            elif(comp_type=="logs"):
                comp=db((db.complaint_logs.Admin1==aid)|(db.complaint_logs.Admin2==aid)).select()
            else:
                raise HTTP(404)
            
        elif (l==3):
            cid=int(request.args[2])
            if (comp_type=="admin_ind"):
                comp=db((db.ind_complaint.id==cid)&(db.ind_complaint.Admin_ID==aid)).select()
                db((db.ind_complaint.id==cid)&(db.ind_complaint.Admin_ID==aid)&(db.ind_complaint.Status==2)).update(Status=6)
            elif (comp_type=="pending"):
                comp=db((db.grp_complaint.id==cid)&(db.grp_complaint.Cur_Admin_ID==aid)&(db.grp_complaint.Status in [2,3,6])).select()
                db((db.grp_complaint.id==cid)&(db.grp_complaint.Cur_Admin_ID==aid)&(db.grp_complaint.Status==2)).update(Status=6)
            elif(comp_type=="logs"):
                comp=db((db.grp_complaint.Complaint_ID==cid)&(db.complaint_logs.Admin1==aid)|(db.complaint_logs.Admin2==aid)).select()
                db((db.grp_complaint.Complaint_ID==cid)&((db.complaint_logs.Admin1==aid)|(db.complaint_logs.Admin2==aid))&(db.ind_complaint.Status==2)).update(Status=6)
                if(len(comp)>0):
                    comp=db(db.grp_complaint.id==cid)
            else:
                raise HTTP(404)
            if (len(comp)>0):
                comp=comp.first()
            else:
                raise HTTP(404)
        else:
            raise HTTP (404)
    return dict(complaints=comp)


def lodge_ind():
    if (set(["title","description","solverid"])>set(request.vars)):
        raise HTTP(404)
    else:
        title=str(request.vars["title"])
        descr=str(request.vars["description"])
        solver_id=int(request.vars["solverid"])
        if (len(db(db.solvers.id==solver_id).select())>0):
            complaint_id=db.ind_complaint.insert(User_ID=auth.user.id,Reg_Date=datetime.now,Title=title,Description=descr,
                                                 Status=1,Solver_ID=solver_id)
            complaint=db(db.ind_complaint.id==complaint_id).select().first()
        else:
            raise HTTP(404)
    return dict(complaint=complaint,success=True)

def lodge_hstl():
    if (set(["title","description","adminid"])>set(request.vars)):
        raise HTTP(404)
    else:
        title=str(request.vars["title"])
        descr=str(request.vars["description"])
        admin_id=int(request.vars["adminid"])
        locality=db(db.users.id==auth.user.id).select().first().locality
        admin=db(db.administrators.id==admin_id).select()
        if (len(admin)>0 and locality!=18):   # A person staying outside IIT can't lodge locality based complaint
            if(admin.first().locality==locality and admin.first().isLeaf):  #Complaints can be addressed at first only to leaf level admins
                complaint_id=db.grp_complaint.insert(User_ID=auth.user.id,Reg_Date=datetime.now,Title=title,Description=descr,
                                                     Status=2,Initial_Admin_ID=admin_id,Cur_Admin_ID=admin_id,locality=locality)
                complaint=db(db.grp_complaint.id==complaint_id).select().first()
            else:
                raise HTTP(404)
        else:
            raise HTTP(404)
    return dict(complaint=complaint,success=True)

def lodge_insti():
    if (set(["title","description","adminid"])>set(request.vars)):
        raise HTTP(404)
    else:
        title=str(request.vars["title"])
        descr=str(request.vars["description"])
        admin_id=int(request.vars["adminid"])
        locality=17
        admin=db(db.administrators.id==admin_id).select()
        if (len(admin)>0):
            if(admin.first().locality==locality and admin.first().isLeaf): #Complaints can be addressed at first only to leaf level admins
                complaint_id=db.grp_complaint.insert(User_ID=auth.user.id,Reg_Date=datetime.now,Title=title,Description=descr,
                                                     Status=2,Initial_Admin_ID=admin_id,Cur_Admin_ID=admin_id,locality=locality)
                complaint=db(db.grp_complaint.id==complaint_id).select().first()
            else:
                raise HTTP(404)
        else:
            raise HTTP(404)
    return dict(complaint=complaint,success=True)
