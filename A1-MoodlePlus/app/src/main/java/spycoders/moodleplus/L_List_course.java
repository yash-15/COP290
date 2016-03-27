package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/23/2016.
 */
public class L_List_course extends L_List<course> {

    public course find(String code) throws DoesNotExistException
    {
        node<course> temp=head;
        while(temp!=null)
        {
            if (temp.value.code.equals(code))
            {
                return temp.value;
            }
            temp=temp.next;
        }
        throw new DoesNotExistException();
    }
}

class DoesNotExistException extends Exception
{

}
