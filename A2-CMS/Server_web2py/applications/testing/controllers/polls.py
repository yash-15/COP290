# -*- coding: utf-8 -*-
# try something like
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
