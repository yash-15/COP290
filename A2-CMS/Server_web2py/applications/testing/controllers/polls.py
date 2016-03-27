# -*- coding: utf-8 -*-
# try something like

from datetime import datetime,timedelta

def index(): return dict(message="hello from polls.py")

#All date time formats need to be sent in the format of "MMM DD YYYY HR:MM(AM/PM)"
#example: "FEB 20 1902 12:09PM"

@auth.requires_login()
def new():
    try:
        aid=int(request.args[0])
        cid=int(request.args[1])
        title=str(request.vars["title"])
        lastdate=datetime.strptime(str(request.vars["lastdate"]),'%b %d %Y %I:%M%p')
        choices=str(request.vars["choices"]).split()  #Underscore should be used instead of spaces
    except Exception,e:
        raise HTTP(404)
        
    #Admin Authentication
    if (authe_admin(aid)):
        comp=db((db.grp_complaint.id==cid)&(db.grp_complaint.Cur_Admin_ID==aid)&(db.grp_complaint.Poll_Results==None)
                &(db.grp_complaint.Status==6)).select()
        if(len(comp)==0):
            raise HTTP(404)
        else:
            votes=[]
            for i in range(len(choices)):
                votes.append(0)
            dttm=datetime.now
            poll=db.polls.insert(Date_Created=dttm,Last_Date=lastdate,Title=title,Choices=choices,Votes=votes)
            db(db.grp_complaint.id==cid).update(Status=3,Poll_Results=poll)
            comp=db(db.grp_complaint.id==cid).select().first()
            log=db.complaint_logs.insert(Complaint_ID=cid,Admin1=aid,action_taken="Poll Create",mod_date=dttm)
    else:
        raise HTTP(404)
    return dict(complaint=comp,poll=db(db.polls.id==poll).select().first(),log=db(db.complaint_logs.id==log).select().first())

@auth.requires_login()
def extend():
    try:
        aid=int(request.args[0])
        cid=int(request.args[1])
        lastdate=datetime.strptime(str(request.vars["lastdate"]),'%b %d %Y %I:%M%p')
    except Exception,e:
        raise HTTP(404)
    
    #Admin Authentication
    if not authe_admin(aid):
        raise HTTP(404)
    comp=db((db.grp_complaint.id==cid)&(db.grp_complaint.Cur_Admin_ID==aid)&(db.grp_complaint.Poll_Results!=None)
            &((db.grp_complaint.Status==3)|(db.grp_complaint.Status==6))).select()
    if (len(comp)==0):
        raise HTTP(404)
    comp=comp.first()
    poll=db(db.polls.id==comp.Poll_Results).select().first()
    if (poll.Last_Date>=lastdate):
        raise HTTP(404)
    db(db.polls.id==comp.Poll_Results).update(Last_Date=lastdate)
    db(db.grp_complaint.id==cid).update(Status=3)
    comp=db(db.grp_complaint.id==cid).select().first()
    poll=db(db.polls.id==comp.Poll_Results).select().first()
    log=db.complaint_logs.insert(Complaint_ID=cid,Admin1=aid,action_taken="Poll Date Extend to "+str(lastdate),mod_date=datetime.now)
    return dict(complaint=comp,poll=poll,log=db(db.complaint_logs.id==log).select().first())

@auth.requires_login()
def vote():
    try:
        cid=int(request.vars["comp_id"])
        choice=int(request.vars["choice"])
    except Exception,e:
        raise HTTP(404)
    comp=db((db.grp_complaint.id==cid)&(db.grp_complaint.Status==3)&((db.grp_complaint.locality==17)|(db.grp_complaint.locality==auth.user.locality))).select()
    if (len(comp)==0):
        raise HTTP(404)
    else:
        comp=comp.first()
        poll=db(db.polls.id==comp.Poll_Results).select().first()
        voted=db((db.votes.poll==poll.id)&(db.votes.user_ID==auth.user.id)).select()
        if (len(poll.Choices)<choice or choice<=0):
            raise HTTP(404)
        else:
            v_list=poll.Votes
            v_list[choice-1]+=1
            if (len(voted)==0):
                db.votes.insert(poll=poll.id,user_ID=auth.user.id,choice=choice)
            else:
                voted=voted.first()
                i_choice=voted.choice
                v_list[i_choice-1]-=1
                db((db.votes.poll==poll.id)&(db.votes.user_ID==auth.user.id)).update(choice=choice)
            db(db.polls.id==poll.id).update(Votes=v_list)
            poll=db(db.polls.id==poll.id).select().first()
            voted=db((db.votes.poll==poll.id)&(db.votes.user_ID==auth.user.id)).select().first()
    return dict(complaint=comp,poll=poll,vote=voted)
