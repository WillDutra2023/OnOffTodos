package br.com.william.onofftodos.domain.di

import br.com.william.onofftodos.domain.usecase.GetTodoDetailUseCase
import br.com.william.onofftodos.domain.usecase.GetTodoDetailUseCaseImpl
import br.com.william.onofftodos.domain.usecase.GetTodosUseCase
import br.com.william.onofftodos.domain.usecase.GetTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.PatchTodosUseCase
import br.com.william.onofftodos.domain.usecase.PatchTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.PostTodosUseCase
import br.com.william.onofftodos.domain.usecase.PostTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.DeleteTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.DeleteTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.FindAllTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.FindAllTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.FindByIdTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.FindByIdTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.SaveAllTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.SaveAllUseCase
import br.com.william.onofftodos.domain.usecase.db.SaveTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.SaveTodosUseCaseImpl
import br.com.william.onofftodos.domain.usecase.db.UpdateTodosUseCase
import br.com.william.onofftodos.domain.usecase.db.UpdateTodosUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetTodosUseCase> { GetTodosUseCaseImpl(get()) }
    factory<GetTodoDetailUseCase> { GetTodoDetailUseCaseImpl(get()) }
    factory<PatchTodosUseCase> { PatchTodosUseCaseImpl(get()) }
    factory<PostTodosUseCase> { PostTodosUseCaseImpl(get()) }
    factory<DeleteTodosUseCase> { DeleteTodosUseCaseImpl(get()) }
    factory<FindAllTodosUseCase> { FindAllTodosUseCaseImpl(get()) }
    factory<FindByIdTodosUseCase> { FindByIdTodosUseCaseImpl(get()) }
    factory<SaveTodosUseCase> { SaveTodosUseCaseImpl(todosDBRepository = get()) }
    factory<SaveAllUseCase> { SaveAllTodosUseCaseImpl(todosDBRepository = get()) }
    factory<UpdateTodosUseCase> { UpdateTodosUseCaseImpl(todosDBRepository = get()) }
}