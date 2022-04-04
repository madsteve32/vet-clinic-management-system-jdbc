package view;

import exception.InvalidEntityDataException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    public static class Option {
        private String text;
        private Command command;

        public Option(String text, Command command) {
            this.text = text;
            this.command = command;
        }

        public String getText() {
            return text;
        }

        public Command getCommand() {
            return command;
        }

        @Override
        public String toString() {
            return "MenuOption{" +
                    "text='" + text + '\'' +
                    ", command=" + command +
                    '}';
        }
    }
    public interface Command {
        String execute() throws InvalidEntityDataException;
    }
    public class ExitCommand implements Command {
        @Override
        public String execute() {
            return String.format("Exiting menu: %s", title);
        }
    }

    // Main class methods and attributes

    private String title;
    private List<Option> options = List.of(new Option("Exit", new ExitCommand()));
    private Scanner scanner = new Scanner(System.in);

    public Menu() {}

    public Menu(String title, List<Option> options) {
        this.title = title;
        var oldOptions = this.options;
        this.options = new ArrayList<>();
        this.options.addAll(options);
        this.options.addAll(oldOptions);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (!Objects.equals(title, menu.title)) return false;
        return Objects.equals(options, menu.options);
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (options != null ? options.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "title='" + title + '\'' +
                ", options=" + options +
                '}';
    }

    public void show() throws InvalidEntityDataException {
        while (true) {
            System.out.printf("MENU: %s%n", title);
            for (int i = 0; i < options.size(); i++) {
                System.out.printf("%2d. %s%n", i + 1, options.get(i).getText());
            }
            int choice = -1;
            do {
                System.out.printf("Enter your choice (1 - %s):", options.size());
                var choiceStr = scanner.nextLine();
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Invalid choice. Please enter a valid number between 1 and " + options.size());
                }
            } while (choice < 1 || choice > options.size());
            var result = options.get(choice - 1).getCommand().execute();
            System.out.println(result);
            if (choice == options.size()) {
                break;
            }
        }
    }
}
