from fastapi import FastAPI, Body, HTTPException, status
from pydantic import BaseModel
from enum import Enum
# from src.schemas import AiFileProcessDto
# from src.schemas import AiFileProcessTask
from transformers import pipeline


app = FastAPI()


class AiFileProcessTask(str, Enum):
    SUMMARIZE = "SUMMARIZE"
    EXTRACT_IDENTIFIERS = "EXTRACT_IDENTIFIERS"
    FILL_GAPS = "FILL_GAPS"
    COMPARE = "COMPARE"

class AiFileProcessDto(BaseModel):
    content: str
    task: AiFileProcessTask | None = None
    user_id: str | None = None



class TaskResponse(BaseModel):
    summary: str


# @app.post(path="/summarize", response_model=Response, status_code=status.HTTP_200_OK)
# async def summarize(text: str = Body(media_type="text/plain")) -> Response:

#     if not text.strip():
#         raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Content is empty.")

#     # Hier geef je 'text' door aan je Transformers pipeline
#     # Voorbeeld: summary_output = summarizer(text, max_length=130, min_length=30)
#     generated_summary = f"Samenvatting van ({len(text)} tekens): {text[:100]}..."

#     # 3. Retourneer het Pydantic model (DTO)
#     return Response(summary=generated_summary)

@app.post(path="/summarize", response_model=TaskResponse, status_code=status.HTTP_200_OK,)
def execute_task(dto: AiFileProcessDto = Body(...)) -> TaskResponse:
    if not dto.content or not dto.content.strip():
        raise HTTPException(status_code=status.HTTP_400_BAD_REQUEST, detail="Content is empty.",)

    print(f"Content length: {len(dto.content)} tekens")

    pipe = pipeline(task="text-generation", model="HuggingFaceTB/SmolLM2-135M-Instruct",)

    messages = [
        {
            "role": "system",
            "content": "You are a helpful assistant that summarizes text concisely.",
        },
        {
            "role": "user",
            "content": f"Summarize the following text:\n\n{dto.content}",
        },
    ]

    outputs = pipe(messages, max_new_tokens=100)
    summary_text = outputs[0]["generated_text"][-1]["content"]

    return TaskResponse(summary=summary_text)


