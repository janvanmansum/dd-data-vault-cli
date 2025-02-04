/*
 * Copyright (C) 2022 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.datavaultcli;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import nl.knaw.dans.datavaultcli.client.ApiException;
import nl.knaw.dans.datavaultcli.client.DefaultApi;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.util.UUID;
import java.util.concurrent.Callable;

@Command(name = "status",
         mixinStandardHelpOptions = true,
         description = "Get the status of a job.")
@RequiredArgsConstructor
public class ImportStatus implements Callable<Integer> {
    @NonNull
    private final DefaultApi api;

    @Parameters(index = "0",
                paramLabel = "id",
                description = "The id of the job.")
    private UUID id;

    @Override
    public Integer call() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            var importJob = api.importsIdGet(id);
            System.err.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(importJob));
            return 0;
        }
        catch (ApiException | JsonProcessingException e) {
            System.err.println("Error: " + e.getMessage());
            return 1;
        }
    }
}
