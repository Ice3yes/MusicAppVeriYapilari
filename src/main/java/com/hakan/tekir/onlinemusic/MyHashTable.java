package com.hakan.tekir.onlinemusic;

//HashTable için gerekli metodlar ve Node'lar
public class MyHashTable {
    MyLinkedList<Music>[] files=new MyLinkedList[25];

    MyHashTable(){
        for (int i = 0; i < 25; i++) {
            files[i]=new MyLinkedList();
        }
    }

    void add(Music music){
        Character ch = music.getName().toLowerCase().charAt(0);
        files[ch%25].add(music);
    }

    Music get(String string){
        if(files[string.charAt(0)]==null){
            return null;
        }else {
            MyLinkedList<Music>.Node<Music> cursor = files[string.charAt(0)].root;
            while (cursor!=null){
                if (cursor.value.getName().equals(string)){
                    return cursor.value;
                }
            }
        }
        return null;
    }
}
