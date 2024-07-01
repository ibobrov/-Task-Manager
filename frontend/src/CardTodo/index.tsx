import React, { FC, useEffect, useRef, useState } from "react";
import { CardTodoProps } from "./types";
import * as S from "./CardTodo.styled";
import {API} from "../API";

const CardTodo: FC<CardTodoProps> = ({setTodoList, id, text, status}) => {
    const [isEdit, setIsEdit] = useState(false);
    const inputRef = useRef<HTMLInputElement>(null);

    const onEdit = () => setIsEdit(true);
    const onBlur = async () => {
        setIsEdit(false);
        if (inputRef.current) {
            await API.putTask(id, {key: id, val: inputRef.current.value})
        }
    }

    const onDelete = async () => {
        await API.deleteTask(id)
        setTodoList(await API.getAll())
    };

    useEffect(() => {
        if (isEdit && inputRef.current) {
            inputRef.current.focus();
        }
    }, [isEdit]);

    return (
        <S.CardTodoContainer>
            <label>
                <input ref={inputRef} type="text" defaultValue={text} onBlur={onBlur}/>
            </label>
            <div>
                <S.Button onClick={onEdit}>Edit</S.Button>
                <S.Button onClick={onDelete}>Delete</S.Button>
            </div>
        </S.CardTodoContainer>
    );
};

export { CardTodo };