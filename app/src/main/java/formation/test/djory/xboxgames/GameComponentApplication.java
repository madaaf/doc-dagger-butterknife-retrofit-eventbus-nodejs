package formation.test.djory.xboxgames;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;
import de.greenrobot.event.EventBus;
import formation.test.djory.xboxgames.activity.CartActivity;
import formation.test.djory.xboxgames.activity.HomeActivity;
import formation.test.djory.xboxgames.service.GameService;
import retrofit.RestAdapter;

/**
 * Created by excilys on 13/11/15.
 */
public class GameComponentApplication extends Application {

    private static GameComponentApplication app;
    private GameComponent component;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        component = DaggerGameComponentApplication_GameComponent.builder().gameModule(new GameModule(this)).build();
    }


    public GameComponent getComponent() {
        return component;
    }

    public static GameComponentApplication app() {
        return app;
    }

    @Component(modules = {GameModule.class})
    @Singleton
    public interface GameComponent {
        void inject(HomeActivity activity);
        void inject(CartActivity activity);
    }

    @Module
    public class GameModule {
        private final GameComponentApplication app;
        private final RestAdapter restAdapter;
        public GameModule(GameComponentApplication app){
            this.app = app;
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(BuildConfig.rootUrl)
                    .build();

        }

        @Provides
        GameComponentApplication providesApp(){
            return app;
        }

        @Provides
        @Singleton
        public GameService providesRestAdapter() {
            return restAdapter.create(GameService.class);
        }

        @Provides
        @Singleton
        public EventBus providesBus() {
            return EventBus.getDefault();
        }

    }

}
