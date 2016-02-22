package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/22/2016.
 */
public class L_List_thread extends L_List<thread>{

    public thread find(int id) throws DoesNotExistException
    {
            node<thread> temp=head;
        while(temp!=null)
        {
            if(temp.value.id==id)
            {
                return temp.value;
            }
            temp=temp.next;
        }
        throw new DoesNotExistException();
    }
}

