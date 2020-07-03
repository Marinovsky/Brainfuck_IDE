/*
 * Nodoo change this license header, choose License Headers in Project Properties.
 * Nodoo change this template file, choose Nodoools | Nodoemplates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * @author david
 */
public class Tree{
    TNodo root;
    
    public Tree(){
        root = null;
    }
    
    //insertar
    public void insert(TNodo nuevo) {
        if(isEmpty()) root = nuevo;
        else root=insert(root, nuevo);
    }
    private TNodo insert(TNodo nodo, TNodo nuevo){
        if (nuevo.getKey() > nodo.getKey()) //si key es mayor, nodo irá a la rama derecha
            if(nodo.getRight()!=null)
                nodo.setRight(insert(nodo.getRight(), nuevo));
            else nodo.setRight(nuevo);
        else nodo.setRight(nuevo);
        return balancear(nodo);
    }

    //Balance
    private int altura(TNodo nodo) {
        if(isEmpty(nodo)) return -1;
        else return nodo.getHeight();
    }
    private int establecerAltura(TNodo nodo) {
        if(isEmpty(nodo)) return -1;
        else return Math.max(altura(nodo.getLeft()), altura(nodo.getRight())) + 1;
    }
    private int factorBalance(TNodo nodo){
        /*
        factor balance:
        [ mayor a 1  ] ---> cargado a la izquierda
        [ -1,0,1     ] ---> equilibrado
        [ menor a -1 ] ---> cargado a la derecha
        */
        return altura(nodo.getLeft()) - altura(nodo.getRight());
    }
    private TNodo balancear(TNodo nodo){
        int factorBalance = factorBalance(nodo);
        if(factorBalance > 1) {
            if(altura(nodo.getLeft().getLeft()) >= altura(nodo.getLeft().getRight())) //caso left left (RSD)
                nodo = rotacionAlaDerecha(nodo);
            else {
                nodo.setLeft(rotacionAlaIzquierda(nodo.getLeft())); //caso left right (RDD)
                nodo = rotacionAlaDerecha(nodo);
            }
        }else if(factorBalance < -1) {
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
    private TNodo rotacionAlaIzquierda(TNodo nodo) {
        TNodo temp = nodo.getRight();
        nodo.setRight(temp.getLeft());
        temp.setLeft(nodo);
        
        nodo.setHeight(establecerAltura(nodo));
        temp.setHeight(establecerAltura(temp));
        return temp;
    }
    private TNodo rotacionAlaDerecha(TNodo nodo) {
        TNodo temp = nodo.getLeft();
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
    private TNodo remove(TNodo nodo, int key) {
        if (key < nodo.getKey()) nodo.setLeft(remove(nodo.getLeft(), key));
        else if (key > nodo.getKey()) nodo.setRight(remove(nodo.getRight(), key));
        else{
            if(nodo.getRight() != null)
                if(nodo.getLeft() == null) nodo=nodo.getRight();        //caso con rama derecha
                else {                                                  //caso con dos hijos
                    TNodo temp = lowerNodo(nodo.getRight());
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
    public TNodo get(int key){
        return buscar(root,key);
    }
    private TNodo buscar(TNodo nodo, int key){
        if(key > nodo.getKey()) return buscar(nodo.getRight(),key);
        else if(key < nodo.getKey()) return buscar(nodo.getLeft(),key);
        else return nodo;
    }
    public boolean isEmpty(){
        return isEmpty(root);
    }
    private boolean isEmpty(TNodo nodo){
        return nodo==null;
    }
    public TNodo lowerNodo(TNodo nodo){
        if(nodo.getLeft()!=null) return lowerNodo(nodo.getLeft());
        else return nodo;
    }
    private void afterRemove(TNodo nodo, int key) { 
        if (nodo != null) {
            afterRemove(nodo.getLeft(), key);
            if(nodo.getKey()>key) nodo.setKey(nodo.getKey()-1);
            afterRemove(nodo.getRight(), key);
        }
    }
    public void inOrder(){
        TNodo temp = root;
        inOrder(temp);
        System.out.print("\n");
    }
    public void inOrder(TNodo node) { 
        if (node != null) { 
            inOrder(node.getLeft());
            System.out.print("key: " + node.getKey() + " altura: " + node.getHeight() + " ");
            inOrder(node.getRight());
        } 
    } 
}