package com.example.bankProject

import com.example.bankProject.accounts.CreateAccountRequest
import com.example.bankProject.authentication.jwt.JwtService
import com.example.bankProject.users.UserEntity
import com.example.bankProject.users.UsersRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.MultiValueMap
import java.math.BigDecimal
import org.springframework.test.context.ActiveProfiles



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class BankProjectApplicationTests {

	companion object {
		var testUserId: Long? = null

		@JvmStatic
		@BeforeAll
		fun setUp(
			@Autowired usersRepository: UsersRepository,
			@Autowired passwordEncoder: PasswordEncoder,
		){
			usersRepository.deleteAll()
			val testUser = UserEntity(
				username = "coded",
				password = passwordEncoder.encode("joincoded")
			)
			usersRepository.save(testUser)
			val user = usersRepository.findByUsername(testUser.username)
			testUserId = user!!.id
		}
	}

	@Autowired
	lateinit var restTemplate: TestRestTemplate

	@Test
	fun contextLoads() {
	}

	@Test
	fun createMultipleAccounts(@Autowired jwtService: JwtService){
		val token = jwtService.generateToken("coded")
		val headers = HttpHeaders(
			MultiValueMap.fromSingleValue(mapOf("Authorization" to "Bearer $token"))
		)
		var maximumNumber = 5

		val finalRequest = CreateAccountRequest(testUserId!!, "New Account", BigDecimal(1800))
		val requestEntity = HttpEntity(finalRequest, headers)

	// list of requests
		while(maximumNumber!=0){
			val response = restTemplate.exchange(
				"/accounts/v1/accounts",
				HttpMethod.POST,
				requestEntity,
				String::class.java
			)
			assertEquals(HttpStatus.OK, response.statusCode)
			assertEquals("Account created successfully", response.body)
			maximumNumber-=1
		}
// the extra request
		val result = restTemplate.exchange(
			"/accounts/v1/accounts",
			HttpMethod.POST,
			requestEntity,
			String::class.java
		)
		assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
		assertEquals("You have reached your maximum accounts", result.body)



	}

}
