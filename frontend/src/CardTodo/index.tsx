import React, { FC, useEffect, useRef, useState } from "react";
import { CardTodoProps } from "./types";
import * as S from "./CardTodo.styled";

const CardTodo: FC<CardTodoProps> = ({text}) => { 
    const [stateText, setStateText] = useState(text);
    const [isEdit, setIsEdit] = useState(false);
    const inputRef = useRef(null);

    const onEdit = () => setIsEdit(true);
    const onBlur = () => setIsEdit(false);

    const onDelete = () => {

    };

    useEffect(() => {
        if (isEdit && inputRef.current) {
            // @ts-ignore
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