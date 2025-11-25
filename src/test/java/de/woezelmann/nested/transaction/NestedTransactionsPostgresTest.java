package de.woezelmann.nested.transaction;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@Testcontainers
@SpringBootTest(classes = NestedTransactionsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class NestedTransactionsPostgresTest {

		@Container
		public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.1");

		@DynamicPropertySource
		static void configureProperties(DynamicPropertyRegistry registry) {
				registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
				registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
				registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
		}

		@Autowired
		private OuterService outerService;

		@Test
		void runWithOutsideTransaction() {
				outerService.runWithTransaction();
		}
}
