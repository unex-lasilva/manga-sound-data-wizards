package br.com.mangarosa.collections;

/**
 * A classe {@code ListaEncadeada} implementa uma estrutura de dados de lista encadeada simples.
 * Ela permite adicionar, remover e acessar elementos de maneira eficiente. Esta lista pode armazenar
 * qualquer tipo de objeto e oferece funcionalidades típicas de listas, como adicionar ao final,
 * inserir em posições específicas, remover elementos, verificar a presença de elementos e mais.
 *
 * <p>Os principais métodos incluem:</p>
 * <ul>
 *   <li>{@link #append(Object)} - Adiciona um elemento ao final da lista.</li>
 *   <li>{@link #insertAt(int, Object)} - Insere um elemento em uma posição específica.</li>
 *   <li>{@link #addAll(ListaEncadeada)} - Adiciona todos os elementos de outra lista à lista atual.</li>
 *   <li>{@link #remove(int)} - Remove um elemento de uma posição específica.</li>
 *   <li>{@link #clear()} - Limpa todos os elementos da lista.</li>
 *   <li>{@link #get(int)} - Retorna o elemento na posição especificada.</li>
 *   <li>{@link #isEmpty()} - Verifica se a lista está vazia.</li>
 *   <li>{@link #size()} - Retorna o número de elementos na lista.</li>
 *   <li>{@link #indexOf(Object)} - Retorna o índice da primeira ocorrência do valor especificado.</li>
 *   <li>{@link #contains(Object)} - Verifica se o valor especificado está na lista.</li>
 * </ul>
 *
 * @author Mangarosa
 * @version 1.0
 */

public class ListaEncadeada {
    private No cabeca;
    private int tamanho = 0;

    private static class No {
        Object valor;
        No proximo;

        No(Object valor) {
            this.valor = valor;
        }
    }

    public void append(Object value) {
        No novo = new No(value);
        if (cabeca == null) {
            cabeca = novo;
        } else {
            No atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

    public boolean remove(int position) {
        if (position < 0 || position >= tamanho) {
            return false;
        }

        if (position == 0) {
            cabeca = cabeca.proximo;
        } else {
            No anterior = cabeca;
            for (int i = 0; i < position - 1; i++) {
                anterior = anterior.proximo;
            }
            anterior.proximo = anterior.proximo.proximo;
        }
        tamanho--;
        return true;
    }

    public void insertAt(int position, Object value) {
        if (position < 0 || position > tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        No novo = new No(value);
        if (position == 0) {
            novo.proximo = cabeca;
            cabeca = novo;
        } else {
            No anterior = cabeca;
            for (int i = 0; i < position - 1; i++) {
                anterior = anterior.proximo;
            }
            novo.proximo = anterior.proximo;
            anterior.proximo = novo;
        }
        tamanho++;
    }

    public boolean isEmpty() {
        return tamanho == 0;

    }

    public int size() {
        return tamanho;
    }

    public void addAll(ListaEncadeada list) {
        for (int i = 0; i < list.size(); i++) {
            this.append(list.get(i));
        }
    }

    public int indexOf(Object value) {
        No atual = cabeca;
        int index = 0;
        while (atual != null) {
            if (atual.valor.equals(value)) {
                return index;
            }
            atual = atual.proximo;
            index++;
        }
        return -1;
    }

    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }

    public boolean clear() {
        cabeca = null;
        tamanho = 0;
        return true;
    }

    public Object get(int position) {
        if (position < 0 || position >= tamanho) {
            throw new IndexOutOfBoundsException("Posição inválida");
        }

        No atual = cabeca;
        for (int i = 0; i < position; i++) {
            atual = atual.proximo;
        }
        return atual.valor;
    }
}