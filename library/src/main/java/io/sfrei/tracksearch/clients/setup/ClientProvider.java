package io.sfrei.tracksearch.clients.setup;

import io.sfrei.tracksearch.config.TrackSearchConfig;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
class ClientProvider {

    protected static final OkHttpClient okHttpClient;

    static {
        TrackSearchConfig.load();
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new CustomInterceptor())
                .build();
    }

    private static final class CustomInterceptor implements Interceptor {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            int code = response.code();
            if (code != Client.OK) {
                log.error("ErrorOnRequest -> {} - Code: {}", request.url(), code);
            }
            return response;
        }
    }

}
