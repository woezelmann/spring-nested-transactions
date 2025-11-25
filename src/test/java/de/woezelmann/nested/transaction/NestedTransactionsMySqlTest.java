package de.woezelmann.nested.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.mysql.MySQLContainer;

import java.util.UUID;

@Testcontainers
@SpringBootTest(classes = NestedTransactionsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NestedTransactionsMySqlTest {

		@Container
		public static MySQLContainer mysql = new MySQLContainer("mysql:8.0.36");

		@DynamicPropertySource
		static void configureProperties(DynamicPropertyRegistry registry) {
				registry.add("spring.datasource.url", mysql::getJdbcUrl);
				registry.add("spring.datasource.username", mysql::getUsername);
				registry.add("spring.datasource.password", mysql::getPassword);
		}

		@Autowired
		private OuterService outerService;

		@Test
		void runWithOutsideTransaction() {
				outerService.runWithTransaction();
		}
}
