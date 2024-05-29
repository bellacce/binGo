package com.wenhui.common.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

/**
 * Properties are configured in the application.text file.
 * 用来注册自定义配置文件
 */
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

	/*private final Security security = new Security();
	@Data
	public static class Security {

		private final Authentication authentication = new Authentication();

		@Data
		public static class Authentication {

			private final Jwt jwt = new Jwt();

			@Data
			public static class Jwt {

				private String secret;
				private long tokenValidityInSeconds = 1800;
				private long tokenValidityInSecondsForRememberMe = 2592000;

			}
		}
	}*/

    private final Async async = new Async();

    @Data
    public static class Async {

        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 10000;
        private String threadNamePrefix = "Executor-";

    }

    private final Sms sms = new Sms();

    @Data
    public static class Sms {

        private Boolean testSend = Boolean.FALSE;
        private String from;
        private String desc;
        private String url;
        private String account;
        private String pwd;
        private String company;
        private String provider;

        private Verifycode verifycode = new Verifycode();

        @Data
        public static class Verifycode {

            private String defaulted = "1234";

            private int length = 4;
            private int valid_minutes = 3;

            private int per_hour_times = 3;
            private int per_day_times = 10;

        }

    }

    private final HttpClient httpclient = new HttpClient();

    @Data
    public static class HttpClient {

        private HttpClientPool pool = new HttpClientPool();
        private HttpClientTimeout standard = new HttpClientTimeout();
        private HttpClientTimeout wld = new HttpClientTimeout();

        @Data
        public static class HttpClientTimeout {

            private int socket_timeout = 10000;
            private int connect_timeout = 10000;

        }

        @Data
        public static class HttpClientPool {

            private int max_total = 500;
            private int max_per_route = 100;

        }
    }

}
