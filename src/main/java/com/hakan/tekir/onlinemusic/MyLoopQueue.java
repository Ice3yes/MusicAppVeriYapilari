package com.hakan.tekir.onlinemusic;

import javafx.scene.control.Button;

//Çember Kuyruk için gerekli metodlar ve Node'lar
public class MyLoopQueue {
    Node cursor;
    int size=0;

    public void add(Button value){
        if(cursor==null){
            cursor=new Node(value);
            cursor.next=cursor;
            cursor.pre=cursor;
        } else {
            Node node = new Node(value);
            node.next=cursor;
            node.pre=cursor.pre;
            node.pre.next=node;
            cursor.pre=node;
        }
        size++;
    }

    public void enableAll(){
        for (int i = 0; i < size; i++) {
            cursor.value.setDisable(false);
            cursor=cursor.next;
        }
    }

    class Node{
        Node next, pre;
        Button value;

        public Node(Button value) {
            this.value = value;
        }
    }
}
