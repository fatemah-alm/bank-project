package com.example.bankProject.authentication



import com.example.bankProject.authentication.jwt.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.*
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthenticationRequest): ResponseEntity<*> {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        val authentication = authenticationManager.authenticate(authToken)
        try {
            authenticationManager.authenticate(authToken)
        } catch (e: BadCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password")
        } catch (e: DisabledException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is disabled")
        } catch (e: LockedException) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is locked")
        }

        val token = jwtService.generateToken(request.username)
        return ResponseEntity.ok(AuthenticationResponse(token))
    }
}

data class AuthenticationRequest(
    val username: String,
    val password: String
)

data class AuthenticationResponse(
    val token: String
)