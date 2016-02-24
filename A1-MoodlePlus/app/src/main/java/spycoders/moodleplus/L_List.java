package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/23/2016.
 */
public class L_List<E> {
    node<E> head,tail;
    int num_elements;

    L_List()
    {
        head=null;tail=null;
        num_elements=0;
    }
    public void insert(E data)
    {
        node<E> temp=new node<E>(data);
        if (num_elements==0)
        {
            head=temp;
        }
        else
        {
            tail.next=temp;
        }
        tail=temp;
        num_elements++;
    }

    public void clear()
    {
        num_elements=0;
        head=null;tail=null;
    }

}

