package spycoders.moodleplus;

/**
 * Created by Prabhu on 2/20/2016.
 */
public class Queue<E> {
    node<E> header,trailer;
    int num_elements;

    public E front() throws EmptyQueueException
    {
        if (num_elements==0)
        {
            throw new EmptyQueueException();
        }
        else
        {
            return header.value;
        }
    }

    public void enqueue(E data)
    {
        node<E> temp=new node<E>(data);
        if (num_elements==0)
        {
            header=temp;
        }
        else
        {
            trailer.next=temp;
        }
        trailer=temp;
        num_elements++;
    }

    public E dequeue() throws EmptyQueueException{
        if (num_elements==0)
        {
            throw new EmptyQueueException();
        }
        else
        {
            node<E> temp=header;
            header=header.next;
            num_elements--;
            return temp.value;
        }
    }

}

class EmptyQueueException extends Exception{

};
