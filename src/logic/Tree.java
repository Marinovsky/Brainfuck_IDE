/*
 * Nodoo change this license header, choose License Headers in Project Properties.
 * Nodoo change this template file, choose Nodoools | Nodoemplates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author david
 * @param <T>
 */
public class Tree <T>{
    TNode root;
    
    public Tree(){
        root = null;
    }
    
    //insertar
    public void insert(TNode nuevo) {
        if(isEmpty()) root = nuevo;
        else root=insert(root, nuevo);
    }
    private TNode insert(TNode nodo, TNode nuevo){
        if (nuevo.getKey() > nodo.getKey()) //si key es mayor, nodo irá a la rama derecha
            if(nodo.getRight()!=null)
                nodo.setRight(insert(nodo.getRight(), nuevo));
            else nodo.setRight(nuevo);
        else if(nuevo.getKey() < nodo.getKey()) //si key es menor, nodo irá a la rama izquierda
            if(nodo.getLeft()!=null)
                nodo.setLeft(insert(nodo.getLeft(), nuevo));
            else
                nodo.setLeft(nuevo);
        else nodo.setRight(nuevo);
        return balancear(nodo);
    }

    //Balance
    private int altura(TNode nodo) {
        if(isEmpty(nodo)) return -1;
        else return nodo.getHeight();
    }
    private int establecerAltura(TNode nodo) {
        if(isEmpty(nodo)) return -1;
        else return Math.max(altura(nodo.getLeft()), altura(nodo.getRight())) + 1;
    }
    private int factorBalance(TNode nodo){
        /*
        factor balance:
        [ mayor a 1  ] ---> cargado a la izquierda
        [ -1,0,1     ] ---> equilibrado
        [ menor a -1 ] ---> cargado a la derecha
        */
        return altura(nodo.getLeft()) - altura(nodo.getRight());
    }
    private TNode balancear(TNode nodo){
        int factorBalance = factorBalance(nodo);
        if(factorBalance >= 1) {
            if(altura(nodo.getLeft().getLeft()) >= altura(nodo.getLeft().getRight())) //caso left left (RSD)
                nodo = rotacionAlaDerecha(nodo);
            else {
                nodo.setLeft(rotacionAlaIzquierda(nodo.getLeft())); //caso left right (RDD)
                nodo = rotacionAlaDerecha(nodo);
            }
        }else if(factorBalance <= -1) {
            if(altura(nodo.getRight().getRight()) >= altura(nodo.getRight().getLeft())) // caso right right (RSI)
                nodo = rotacionAlaIzquierda(nodo);
            else {
                nodo.setRight(rotacionAlaDerecha(nodo.getRight())); //caso right left (RDI)
                nodo = rotacionAlaIzquierda(nodo);
            } 
        }else nodo.setHeight(establecerAltura(nodo));
        return nodo;
    }

    //Rotaciones
    private TNode rotacionAlaIzquierda(TNode nodo) {
        TNode temp = nodo.getRight();
        nodo.setRight(temp.getLeft());
        temp.setLeft(nodo);
        
        nodo.setHeight(establecerAltura(nodo));
        temp.setHeight(establecerAltura(temp));
        return temp;
    }
    private TNode rotacionAlaDerecha(TNode nodo) {
        TNode temp = nodo.getLeft();
        nodo.setLeft(temp.getRight());
        temp.setRight(nodo);
        
        nodo.setHeight(establecerAltura(nodo));
        temp.setHeight(establecerAltura(temp));
        return temp;
    }
    
    //Eliminar
    public void remove(int key){
        root=remove(root, key);
        afterRemove(root, key);
    }
    private TNode remove(TNode nodo, int key) {
        if (key < nodo.getKey()) nodo.setLeft(remove(nodo.getLeft(), key));
        else if (key > nodo.getKey()) nodo.setRight(remove(nodo.getRight(), key));
        else{
            if(nodo.getRight() != null)
                if(nodo.getLeft() == null) nodo=nodo.getRight();        //caso con rama derecha
                else {                                                  //caso con dos hijos
                    TNode temp = lowerNodo(nodo.getRight());
                    nodo.setArchivo(temp.getArchivo());
                    nodo.setRight(remove(nodo.getRight(), temp.getKey()));
                }
            else if(nodo.getRight() == null)
                if(nodo.getLeft() != null) nodo=nodo.getLeft();         //caso con rama izquierda
                else return null;                                       //caso sin ramas
        }
        return balancear(nodo);
    }
    
    //Auxiliares
    public TNode get(int key){
        return buscar(root,key);
    }
    /*
    public boolean existe(int key) {
        return existe(root, key, false);
    }
    private boolean existe(TNode nodo, int key, boolean existe) {
        if (existe!=true && nodo!=null){
            if(nodo.getKey()==key) existe=true;
            existe=existe(nodo.getLeft(), key, existe);
            existe=existe(nodo.getRight(), key, existe);
        }
        return existe;
    }
*/
    private TNode buscar(TNode nodo, int key){
        if(key > nodo.getKey()) return buscar(nodo.getRight(),key);
        else if(key < nodo.getKey()) return buscar(nodo.getLeft(),key);
        else return nodo;
    }
    public boolean isEmpty(){
        return root==null;
    }
    public boolean isEmpty(TNode nodo){
        return nodo==null;
    }
    public TNode lowerNodo(TNode node){
        TNode temp = node;
        while(temp.getLeft() != null){
            temp = temp.getLeft();
        }
        return temp;
    }
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(TNode nodo) {
        if (nodo != null) {
            inOrder(nodo.getLeft());
            System.out.print("key: " + nodo.getKey() + " altura: " + nodo.getHeight() + " | ");
            inOrder(nodo.getRight());
        }
    }
    private void afterRemove(TNode nodo, int key) { 
        if (nodo != null) {
            afterRemove(nodo.getLeft(), key);
            if(nodo.getKey()>key) nodo.setKey(nodo.getKey()-1);
            afterRemove(nodo.getRight(), key);
        }
    }
}