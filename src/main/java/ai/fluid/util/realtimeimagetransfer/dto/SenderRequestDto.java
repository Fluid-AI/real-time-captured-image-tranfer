package ai.fluid.util.realtimeimagetransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class SenderRequestDto {
    String userName;
    LocalDate requestedAt;
}
