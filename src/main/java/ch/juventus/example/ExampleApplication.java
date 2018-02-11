package ch.juventus.example;

import ch.juventus.example.data.Department;
import ch.juventus.example.data.DepartmentRepository;
import ch.juventus.example.data.Employee;
import ch.juventus.example.data.Account;
import ch.juventus.example.data.Role;
import ch.juventus.example.data.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

    @Component
    class initRepositoryCLR implements CommandLineRunner {

        private final DepartmentRepository departmentRepository;
        private final AccountRepository accountRepository;

        @Autowired
        public initRepositoryCLR(DepartmentRepository departmentRepository, AccountRepository accountRepository) {
            this.departmentRepository = departmentRepository;
            this.accountRepository = accountRepository;
        }

        @Override
        public void run(String... strings) throws Exception {
            Department accounting = new Department("Accounting");
            accounting.addEmployee(new Employee("Tim", "Taylor"));
            accounting.addEmployee(new Employee("Al", "Borland"));
            accounting.addEmployee(new Employee("Wilson", "Wilson"));
            accounting.addEmployee(new Employee("Bob", "Vila"));
            departmentRepository.save(accounting);

            Role userRole = new Role("user");
            Account bob = new Account("bob", "secret");
            bob.addRole(userRole);
            accountRepository.save(bob);
        }
    }


}
