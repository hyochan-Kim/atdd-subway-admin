package wooteco.subway.admin.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import wooteco.subway.admin.domain.Line;
import wooteco.subway.admin.domain.LineStation;
import wooteco.subway.admin.dto.LineStationCreateRequest;
import wooteco.subway.admin.repository.LineRepository;
import wooteco.subway.admin.repository.StationRepository;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LineServiceTest {
    @Mock
    private LineRepository lineRepository;
    @Mock
    private StationRepository stationRepository;

    private Line line;
    private LineService lineService;

    @BeforeEach
    void setUp() {
        line = new Line(1L, "2호선", LocalTime.of(05, 30), LocalTime.of(22, 30), 5, "test_color");
        lineService = new LineService(lineRepository, stationRepository);

        line.addLineStation(new LineStation(null, 1L, 10, 10));
        line.addLineStation(new LineStation(1L, 2L, 10, 10));
        line.addLineStation(new LineStation(2L, 3L, 10, 10));
    }

    @Test
    void addLineStationAtTheFirstOfLine() {
        LineStationCreateRequest request = new LineStationCreateRequest(null, 4L, 10, 10);

        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.addLineStation(line.getId(), request.toLineStation());

        assertThat(line.getLineStations()).hasSize(4);
        assertThat(line.getStationIds()).hasSize(4);
        assertThat(line.getStationIds().get(0)).isEqualTo(4L);
        assertThat(line.getStationIds().get(1)).isEqualTo(1L);
        assertThat(line.getStationIds().get(2)).isEqualTo(2L);
        assertThat(line.getStationIds().get(3)).isEqualTo(3L);
    }

    @Test
    void addLineStationBetweenTwo() {
        LineStationCreateRequest request = new LineStationCreateRequest(1L, 4L, 10, 10);

        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.addLineStation(line.getId(), request.toLineStation());

        assertThat(line.getLineStations()).hasSize(4);
        assertThat(line.getStationIds()).hasSize(4);
        assertThat(line.getStationIds().get(0)).isEqualTo(1L);
        assertThat(line.getStationIds().get(1)).isEqualTo(4L);
        assertThat(line.getStationIds().get(2)).isEqualTo(2L);
        assertThat(line.getStationIds().get(3)).isEqualTo(3L);
    }

    @Test
    void addLineStationAtTheEndOfLine() {
        LineStationCreateRequest request = new LineStationCreateRequest(3L, 4L, 10, 10);

        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.addLineStation(line.getId(), request.toLineStation());

        assertThat(line.getLineStations()).hasSize(4);
        assertThat(line.getStationIds()).hasSize(4);
        assertThat(line.getStationIds().get(0)).isEqualTo(1L);
        assertThat(line.getStationIds().get(1)).isEqualTo(2L);
        assertThat(line.getStationIds().get(2)).isEqualTo(3L);
        assertThat(line.getStationIds().get(3)).isEqualTo(4L);
    }

    @Test
    void removeLineStationAtTheFirstOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 1L);

        assertThat(line.getLineStations()).hasSize(2);
        assertThat(line.getStationIds()).hasSize(2);
        assertThat(line.getStationIds().get(0)).isEqualTo(2L);
        assertThat(line.getStationIds().get(1)).isEqualTo(3L);
    }

    @Test
    void removeLineStationBetweenTwo() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 2L);

        assertThat(line.getLineStations()).hasSize(2);
        assertThat(line.getStationIds().get(0)).isEqualTo(1L);
        assertThat(line.getStationIds().get(1)).isEqualTo(3L);
    }

    @Test
    void removeLineStationAtTheEndOfLine() {
        when(lineRepository.findById(line.getId())).thenReturn(Optional.of(line));
        lineService.removeLineStation(line.getId(), 3L);

        assertThat(line.getLineStations()).hasSize(2);
        assertThat(line.getStationIds().get(0)).isEqualTo(1L);
        assertThat(line.getStationIds().get(1)).isEqualTo(2L);
    }
}
