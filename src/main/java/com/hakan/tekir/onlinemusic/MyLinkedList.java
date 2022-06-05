package com.hakan.tekir.onlinemusic;

//LinkedList için gerekli metodlar ve Node'lar
public class MyLinkedList <T> {
    Node<T> root;

    public void add(T value){
        if (root==null){
            root = new Node<T>(value);
        } else {
            Node<T> cursor = root;
            while (cursor.next!=null){
                cursor=cursor.next;
            }
            cursor.next=new Node<T>(value);
        }
    }

    public void remove(T value){
        Node<T> cursor = root;
        Node<T> temp=cursor;
        while (cursor!=null){
            if (cursor.value.equals(value)){
                temp.next=cursor.next;
            }
            temp=cursor;
            cursor=cursor.next;
        }
    }

    public int size(){
        int counter=0;
        Node<T> cursor=root;
        while (cursor!=null){
            counter++;
            cursor=cursor.next;
        }
        return counter;
    }

    public T get(int i){
        Node<T> cursor=root;
        for (int j = 0; j < i; j++) {
            cursor=cursor.next;
        }
        return root.value;
    }

    class Node <t>{
        Node<t> next;
        t value;

        public Node(t value) {
            this.value = value;
        }
    }
}
