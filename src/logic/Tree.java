/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;
import data.Archivo;
/**
 *
 * @author david
 */
public class Tree {
  
    public Archivo root;
    int tocount;
    
    public Tree(){
        this.root = new Archivo();
        this.tocount = 0;
    }
    
    //Rotacion y balance
    
    public Archivo rightRotate(Archivo node){ 
        Archivo node2 = node.left; 
        Archivo newnode = node2.right; 
  
        node2.right = node; 
        node.left = newnode; 
  
        node.height = max(height(node.left), height(node.right)) + 1; 
        node2.height = max(height(node2.left), height(node2.right)) + 1; 
  
        return node2; 
    } 
  
    public Archivo leftRotate(Archivo node){ 
        Archivo node2 = node.right; 
        Archivo newnode = node2.left; 
  
        node2.left = node; 
        node.right = newnode; 
  
        node.height = max(height(node.left), height(node.right)) + 1; 
        node2.height = max(height(node2.left), height(node2.right)) + 1; 
  
        return node2; 
    } 
  
    public int getBalance(Archivo N){ 
        if (N == null) return 0; 
  
        return height(N.left) - height(N.right); 
    }
    
    
    //Incertar
    
    public Archivo insert (Archivo data){
        if(isEmpty()) root = new Archivo();
        int key = higherNode(root).key;
        key++;
        return insert(root,key,data);
    }
    private Archivo insert(Archivo node,int key, Archivo data){ 
  
        if (node == null) return (new Archivo(key)); 
  
        if (key < node.key){
            node.left = insert(node.left, key, data);
        } else if (key > node.key){
            node.right = insert(node.right, key, data);
        } else{
            return node;
        } 
  
        node.height = 1 + max(height(node.left),height(node.right)); 
  
        int balance = getBalance(node); 
  
        if(balance > 1 && key < node.left.key){
            return rightRotate(node);
        }
  
        if(balance < -1 && key > node.right.key){
            return leftRotate(node);
        }
  
        if(balance > 1 && key > node.left.key){ 
            node.left = leftRotate(node.left); 
            return rightRotate(node); 
        } 
  
        if(balance < -1 && key < node.right.key) { 
            node.right = rightRotate(node.right); 
            return leftRotate(node); 
        } 
  
        return node; 
    }
  
    //Eliminar
    
    public Archivo deleteNode(int key){
        return deleteNode(root,key);
    }
    private Archivo deleteNode(Archivo raiz, int key){  
        if (raiz == null)  
            return raiz;  
  
        if(key < raiz.key){
            raiz.left = deleteNode(root.left, key);
        } else if(key > raiz.key){
            raiz.right = deleteNode(raiz.right, key);
        } else{
            if((raiz.left == null) || (raiz.right == null)){  
                Archivo temp = null;  
                if(temp == raiz.left){
                    temp = raiz.right;
                } else{
                    temp = raiz.left;
                } if(temp == null){  
                    temp = raiz;  
                    raiz = null;  
                } else{
                    raiz = temp;
                }
            } else{  
                Archivo temp = lowerNode(raiz.right);  
                raiz.key = temp.key;  
                raiz.right = deleteNode(raiz.right, temp.key);  
            }
        }  
  
        if (raiz == null) return raiz;
        
        raiz.height = max(height(raiz.left), height(raiz.right)) + 1;  
        int balance = getBalance(raiz);
        if(balance > 1 && getBalance(raiz.left) >= 0){ 
            return rightRotate(raiz);
        } 
        if(balance > 1 && getBalance(raiz.left) < 0){  
            raiz.left = leftRotate(raiz.left);  
            return rightRotate(raiz);  
        }  
        if(balance < -1 && getBalance(raiz.right) <= 0){
            return leftRotate(raiz);
        }
        if(balance < -1 && getBalance(raiz.right) > 0){  
            raiz.right = rightRotate(raiz.right);  
            return leftRotate(raiz);  
        }
        
        tocount = 0;
        
        afterDelete(raiz);
        
        tocount = 0;
        
        root = raiz;
        
        return root;
    }
    
    public void afterDelete(Archivo raiz){
        if(raiz!=null){
            afterDelete(raiz.left);
            raiz.key = tocount;
            tocount++;
            afterDelete(raiz.right);
        }
    }
    
    public void reset(){
        root = null;
    }
    
    //Encontrar nodo
    
    public boolean search(int data){
        Archivo node = root;
        boolean flag = false;
        while (node != null){
            if (node.key == data) {
                flag = true;
                break;
            }
            if (node.key < data){
                node = node.right;
            }else{
                node = node.left;
            }
        }
        return flag;
    }
    
    public Archivo find(Archivo newnodo, int key){
        if(newnodo.key == key) return newnodo;
        
        if(newnodo.key > key){
            return find(newnodo.left,key);
        }else{
            return find(newnodo.right,key);
        }
    }
    
    public Archivo get(int key){
        if(!search(key)) return null;
        return find(root,key);
        
    }
    
    
    //Contar nodos
    
    public int size(){
        return size(root);
    }
    private int size(Archivo node){
        if (node == null) return 0;
        
        int num = 1;
        num += size(node.left);
        num += size(node.right);
        return num;
    }
    
    
    //Auxiliares
    
    public boolean isEmpty(){
        return root==null;
    }
  
    public int height(Archivo N){ 
        if (N == null) 
            return 0; 
  
        return N.height; 
    } 
  
    public int max(int a, int b){ 
        return (a > b) ? a : b; 
    } 
  
    public void inOrder(Archivo node) { 
        if (node != null) { 
            inOrder(node.left);
            System.out.print(node.key + " ");
            inOrder(node.right);
        } 
    } 
    
    public Archivo lowerNode(Archivo node){  
        Archivo newnode = node;  
  
        while(newnode.left != null){
            newnode = newnode.left;
        }
  
        return newnode;  
    }
    
    public Archivo higherNode(Archivo node){
        
        Archivo newnode = node;
        
        while(newnode.right != null){
            newnode = node.right;
        }
        
        return newnode;
    }
    
}