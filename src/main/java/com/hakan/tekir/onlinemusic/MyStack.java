package com.hakan.tekir.onlinemusic;

//Stack için gerekli metodlar ve Node'lar
public class MyStack<T> {
    Node<T> root=null;
    private int size=0;

    public void push(T value){
        Node<T> node = new Node<>(value);
        node.next =root;
        root=node;
        size++;
    }

    public T pop(){
        if(root!=null){
            T value = root.value;
            root=root.next;
            size--;
            return value;
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size==0;
    }

    class Node <t>{
        Node next;
        t value;

        public Node(t value) {
            this.value = value;
        }
    }
}
