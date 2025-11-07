package no.solea.cargodispatcher;

import no.solea.cargodispatcher.loader.DataLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SoleaCargoDispatcherApplicationTests {
    @MockitoBean
    private DataLoader dataLoader;
	@Test
	void contextLoads() {
	}

}
