package com.hakan.tekir.onlinemusic;

//Kuyruk için gerekli metodlar ve Node'lar
public class MyQueue<T> {
    Node<T> root;

    public void add(T value){
        if(root==null){
            root=new Node<>(value);
        } else {
            Node<T> cursor=root;
            while (cursor.next!=null){
                cursor=cursor.next;
            }
            cursor.next=new Node<>(value);
        }
    }

    public T remove(){
        if (root==null){
            return null;
        } else if(root.next==null){
            T value = root.value;
            root=null;
            return value;
        }

        T value= root.next.value;
        root=root.next;
        return value;
    }

    public boolean isEmpty(){
        return root == null;
    }

    class Node <t>{
        Node<T> next;
        t value;

        public Node(t value) {
            this.value = value;
        }
    }
}
