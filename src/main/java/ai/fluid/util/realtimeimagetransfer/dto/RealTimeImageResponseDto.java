package ai.fluid.util.realtimeimagetransfer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RealTimeImageResponseDto {
    private String imageName;
    private long imageTime;
}
