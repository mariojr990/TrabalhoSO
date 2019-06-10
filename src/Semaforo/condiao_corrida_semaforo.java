/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Semaforo;

/**
 *
 * @author mariojr990
 */
import java.util.concurrent.Semaphore;

public class condiao_corrida_semaforo extends Thread {

    private int idThread;
    private Semaphore semaforo;

    public condiao_corrida_semaforo(int id, Semaphore semaphore) {
        this.idThread = id;
        this.semaforo = semaphore;
    }

    private void processar() {
        try {
            System.out.println("Thread #" + idThread + " processando");
            Thread.sleep((long) (Math.random() * 10000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void entrarRegiaoCritica() {
        System.out.println("Thread #" + idThread + " entrando em região crítica");
        processar();
        System.out.println("Thread #" + idThread + " saindo da região crítica");
    }

    private void entrarRegiaoNaoCritica() {
        System.out.println("Thread #" + idThread + " em região não crítica");
        processar();
    }

    public void run() {
        entrarRegiaoNaoCritica();
        try {
            semaforo.acquire();
            entrarRegiaoCritica();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaforo.release();
        }
    }

    public static void main(String[] args) {
        int numeroDePermicoes = 1;
        int numeroDeProcessos = 4;
        Semaphore semaphore = new Semaphore(numeroDePermicoes);
        condiao_corrida_semaforo[] processos = new condiao_corrida_semaforo[numeroDeProcessos];
        for (int i = 0; i < numeroDeProcessos; i++) {
            processos[i] = new condiao_corrida_semaforo(i, semaphore);
            processos[i].start();
        }
    }
}
