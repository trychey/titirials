/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.examples;

import com.baeldung.examples.guice.Communication;
import com.baeldung.examples.guice.binding.AOPModule;
import com.baeldung.examples.guice.modules.BasicModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.util.Scanner;

/**
 *
 * @author Tayo
 */
public class RunGuice {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule(), new AOPModule());
        Communication comms = injector.getInstance(Communication.class);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("q")) {
                System.exit(0);
            }
            if (input.equalsIgnoreCase("p")) {
                comms.print();
            } else {
                comms.sendMessage(input);
            }

        }

    }
}
