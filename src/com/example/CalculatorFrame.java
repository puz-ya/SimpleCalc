package com.example;

/**
 * Created on 01.02.2017.
 * @author Puzino Yury
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
* Main calc frame
*/
class CalculatorFrame extends JFrame
{
    private JLabel display;
    private JMenuItem menuItem, menuItem2;
    private JMenu menu;
    private JPanel sopPanel;
    private double result;
    private String lastCommand;
    private boolean start;
    private static final Font BIGGER_FONT = new Font("monspaced", Font.PLAIN, 20);
    private static final Font NOTBIGGER_FONT = new Font("monspaced", Font.PLAIN, 14);

    public CalculatorFrame()
    {
        result = 0;
        lastCommand = "=";
        start = true;

        //
        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        menuBar.add(menu);

        //
        ActionListener large = new VAction();
        ActionListener about = new AAction();

        //
        menuItem = new JMenuItem("Expert mode");
        menuItem.addActionListener(large);
        menu.add(menuItem);
        menuItem2 = new JMenuItem("About");
        menuItem2.addActionListener(about);
        menu.add(menuItem2);
        menuBar.add(menu);

        //
        display = new JLabel("0.0");
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(BIGGER_FONT);

        //
        ActionListener insert = new InsertAction();
        //
        ActionListener command = new CommandAction();

        //
        String buttonOrder = "123456789.0<";
        JPanel buttonPanel = new JPanel();
        //
        buttonPanel.setLayout(new GridLayout(5, 3, 2, 2));
        for (int i = 0; i < buttonOrder.length(); i++)
        {
            String keyTop = buttonOrder.substring(i, i+1);
            JButton b = new JButton(keyTop);
            if (keyTop.equals("<")) {
                b.addActionListener(command);
            }
            else {
                b.addActionListener(insert);
            }
            b.setFont(BIGGER_FONT);
            buttonPanel.add(b);
        }

        //
        JPanel opPanel = new JPanel();
        opPanel.setLayout(new GridLayout(5, 1, 2, 2));
        String[] opOrder = {"+", "-", "*", "/", "="};
        for (String oper : opOrder) {
            JButton b = new JButton(oper);
            b.addActionListener(command);
            b.setFont(BIGGER_FONT);
            opPanel.add(b);
        }

        //
        sopPanel = new JPanel();
        sopPanel.setLayout(new GridLayout(5, 1, 2, 2));
        String[] sopOrder = {"sin", "cos", "tg", "ctg", "e^"};
        for (String sop : sopOrder) {
            JButton c = new JButton(sop);
            c.addActionListener(command);
            c.setFont(NOTBIGGER_FONT);
            sopPanel.add(c);
        }
        sopPanel.setVisible(false);

        //
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(BIGGER_FONT);
        clearButton.addActionListener(new ClearListener());

        //
        JPanel clearPanel = new JPanel();
        clearPanel.setLayout(new FlowLayout());
        clearPanel.add(clearButton);

        //
        JPanel fcontent = new JPanel();
        fcontent.setLayout(new BorderLayout(1, 1));
        fcontent.add(opPanel, BorderLayout.WEST);
        fcontent.add(sopPanel, BorderLayout.EAST);

        //
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout(4, 4));
        content.add(display , BorderLayout.NORTH );
        content.add(buttonPanel   , BorderLayout.CENTER);
        content.add(fcontent      , BorderLayout.EAST  );
        content.add(clearPanel    , BorderLayout.SOUTH );

        //
        content.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //
        this.setContentPane(content);
        this.setJMenuBar(menuBar);
        this.pack();
    }

    /**
     * Insert numbers
     */
    private class InsertAction implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            String input = event.getActionCommand();
            String s;
            if (start) {
                display.setText("");
                start = false;
            }
            //
            if (input.equals("."))
            {
                s = display.getText();
                if(!s.contains(".")) display.setText(display.getText() + input);
            }
            else {display.setText(display.getText() + input);}
        }
    }


    /**
     * Perform command (like + )
     */
    private class CommandAction implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();
            String s;
            if(command.equals("<"))
            {
                s = display.getText();
                if(s.length()>1) {
                    s = s.substring(0, s.length() - 1);
                }
                display.setText("" + s);
            }

            /*
            if(command.equals("sin")){
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
                return;
            }
            //*/

            //TODO: think about sin\cos\tan etc
            if (start) {
                lastCommand = command;
            }
            else
            {
                lastCommand = command;
                calculate(Double.parseDouble(display.getText()));
                start = true;
            }
        }
    }

    /** set additional panel visible
     * */
    private class VAction implements ActionListener
    {

        public void actionPerformed(ActionEvent evt)
        {
            String command = evt.getActionCommand();
            if (command.equals("Expert mode"))
            {
                sopPanel.setVisible(true);
                menuItem.setText("Normal mode");
            }
            else
            {
                sopPanel.setVisible(false);
                menuItem.setText("Expert mode");
            }

        }
    }

    private class AAction implements ActionListener
    {
        public void actionPerformed(ActionEvent evt)
        {
            AboutFrame frame2 = new AboutFrame();
            frame2.setDefaultCloseOperation(AboutFrame.DISPOSE_ON_CLOSE);
            frame2.setTitle("About");
            frame2.setResizable(true);
            frame2.setSize(250, 100);
            frame2.setLocation(150,150);
            frame2.setVisible(true);
        }
    }

    class AboutFrame extends JFrame
    {
        public AboutFrame()
        {
            JLabel display_about;
            display_about = new JLabel("Made in 2017 PY.");
            JPanel content_about = new JPanel();
            content_about.add(display_about, BorderLayout.CENTER);
            this.setContentPane(content_about);
            this.pack();
        }
    }

    /**
     *
     * @param x - input number
     */
    public void calculate(double x)
    {
        if (lastCommand.equals("+")) result += x;
        else if (lastCommand.equals("-")) result -= x;
        else if (lastCommand.equals("*")) result *= x;
        else if (lastCommand.equals("/")) result /= x;
        else if (lastCommand.equals("=")) result = x;

        //...
        else if (lastCommand.equals("sin")) result = Math.sin(x);
        else if (lastCommand.equals("cos")) result = Math.cos(x);
        else if (lastCommand.equals("tg")) result = Math.tan(x);
        else if (lastCommand.equals("ctg")) result = 1/Math.tan(x);
        else if (lastCommand.equals("e^")) result = Math.exp(x);
        display.setText(Double.toString(result));
    }


    /**
     * clear all, reset calcs
     */
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            start = true;
            display.setText("0.0");
            lastCommand  = "=";
            result = 0;
        }
    }

}
