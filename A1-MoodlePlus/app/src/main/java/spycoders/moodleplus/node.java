package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/20/2016.
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
