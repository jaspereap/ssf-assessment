package vttp.ssf.assessment.eventmanagement.models;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registration {
    @NotBlank(message = "Must include your name!")
    @Size(min=5, max=25, message="Must be more than 5 characters, and less than 25 characters!")
    private String name;
    
    private LocalDate dob;

    @NotBlank(message = "Must include your email!")
    @Size(max=50, message = "Must be within 50 characters!")
    @Email(message = "Must be a valid email address!")
    private String email;

    @Size(min=8, message = "Must have 8 digits!")
    @Size(max=8, message = "Must have 8 digits!")
    private String mobile;

    @Min(value=1, message = "Must request at least 1 ticket!")
    @Max(value=3, message = "Maximum of 3 tickets!")
    private Integer tickets;
    private String gender;
}
