package co.edu.unbosque.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.google.gson.Gson;

import co.edu.unbosque.model.LoginRequest;
import co.edu.unbosque.model.LoginResponse;

public class AuthService {

	private static final String BACK_BASE_URL = "http://localhost:8081";
	private static final String LOGIN_PATH = "/login/loginEntrada";

	private final HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(8)).build();

	private final Gson gson = new Gson();

	public LoginResponse login(LoginRequest req) throws Exception {
		String url = BACK_BASE_URL + LOGIN_PATH + "?correo=" + encode(req.getCorreo()) + "&contrasena="
				+ encode(req.getContrasena());

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10))
				.POST(HttpRequest.BodyPublishers.noBody()).header("Accept", "application/json").build();

		HttpResponse<String> resp = http.send(request, HttpResponse.BodyHandlers.ofString());

		int sc = resp.statusCode();
		String body = resp.body();

		if (sc == 200) {
			return gson.fromJson(body, LoginResponse.class);
		} else if (sc == 401) {
			throw new IllegalArgumentException("Credenciales inválidas");
		} else {
			throw new IllegalStateException("Error de login. HTTP " + sc + " -> " + body);
		}
	}

	private static String encode(String s) {
		try {
			return java.net.URLEncoder.encode(s, java.nio.charset.StandardCharsets.UTF_8);
		} catch (Exception e) {
			return s;
		}
	}
}