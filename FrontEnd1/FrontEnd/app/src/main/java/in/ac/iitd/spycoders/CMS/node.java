package in.ac.iitd.spycoders.CMS;

/**
 * Created by Prabhu on 3/28/2016.
 */
public class node<E> {
    E value;
    node<E> next;

    node(E data)
    {
        value=data;
        next=null;
    }
}
