package com.learning.resourcelibrary.exception.handlers;

import com.learning.resourcelibrary.exception.ResourceExistsException;
import com.learning.resourcelibrary.exception.ResourceNotFoundException;
import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;

@Component
public class ResourceExceptionHandler implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public DataFetcherExceptionHandlerResult onException(DataFetcherExceptionHandlerParameters handlerParameters) {
        var exception = handlerParameters.getException();
        if (exception instanceof ResourceNotFoundException) {
            var resourceNotFoundError = TypedGraphQLError.newNotFoundBuilder()
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
                    .build();
            return DataFetcherExceptionHandlerResult.newResult().error(resourceNotFoundError).build();
        }

        if (exception instanceof ResourceExistsException) {
            var resourceExistsError = TypedGraphQLError.newBuilder()
                    .message(exception.getMessage())
                    .path(handlerParameters.getPath())
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
            return DataFetcherExceptionHandlerResult.newResult().error(resourceExistsError).build();
        }
        return defaultHandler.onException(handlerParameters);
    }
}
