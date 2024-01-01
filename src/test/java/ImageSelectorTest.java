import org.junit.Test;
import software.ulpgc.imageviewer.Image;
import software.ulpgc.imageviewer.ImageSelector;

import static org.assertj.core.api.Assertions.assertThat;

public class ImageSelectorTest {
    @Test
    public void should_return_next_image_when_offset_is_less_than_zero() {
        Image image = new MockImageLoader().load();
        assertThat(new ImageSelector(image, 800).first(-1)).isEqualTo(image);
        assertThat(new ImageSelector(image, 800).second(-1)).isEqualTo(image.next());
    }

    @Test
    public void should_return_prev_image_when_offset_is_greater_than_zero() {
        Image image = new MockImageLoader().load();
        assertThat(new ImageSelector(image, 800).first(1)).isEqualTo(image);
        assertThat(new ImageSelector(image, 800).second(1)).isEqualTo(image.prev());
    }

    @Test
    public void should_return_next_of_next_image_when_offset_is_negative_and_greater_than_width() {
        Image image = new MockImageLoader().load();
        assertThat(new ImageSelector(image, 800).first(-801)).isEqualTo(image.next());
        assertThat(new ImageSelector(image, 800).second(-801)).isEqualTo(image.next().next());
    }

    @Test
    public void should_return_prev_of_prev_image_when_offset_is_postivie_and_greater_than_width() {
        Image image = new MockImageLoader().load();
        assertThat(new ImageSelector(image, 800).first(801)).isEqualTo(image.prev());
        assertThat(new ImageSelector(image, 800).second(801)).isEqualTo(image.prev().prev());
    }

}
