import * as z from "zod/v4-mini";

const UploadedData = z.object({
  fileName: z.string(),
  token: z.string(),
  url: z.string(),
});

const UploadedDataArray = z.array(UploadedData);

type UploadedData = z.infer<typeof UploadedData>;

const addUploaded = (fileName: string, xToken: string, url: string) => {
  const json: UploadedData = {
    fileName,
    token: xToken,
    url
  };
  try {
    const existData = localStorage.getItem('uploaded-data');
    if (existData) {
      let rawJson = JSON.parse(existData);
      let parseJson = UploadedDataArray.parse(rawJson);
      parseJson.push(json);
      localStorage.setItem('uploaded-data', JSON.stringify(parseJson));
    } else {
      localStorage.setItem('uploaded-data', JSON.stringify([json]));
    }
  } catch {
    localStorage.setItem("uploaded-data", JSON.stringify([json]));
  }
};

function getAllUploaded() {
  try {
    const data = localStorage.getItem('uploaded-data');
    if (data) {
      const rawJson = JSON.parse(data);
      const parseJson = UploadedDataArray.parse(rawJson);
      return parseJson;
    } else {
      return [];
    }
  } catch {
    return [];
  }
}

function getUploaded(url: string): UploadedData | null {
  try {
    const data = localStorage.getItem('uploaded-data');
    if (data) {
      const rawJson = JSON.parse(data);
      const parseJson = UploadedDataArray.parse(rawJson);
      const findedUrl = parseJson.find(u => u.url === url);
      if (findedUrl) {
        return findedUrl;
      } else {
        return null;
      }
    } else {
      return null;
    }
  } catch {
    return null;
  }
};

function getSize(): number {
  const data = localStorage.getItem('uploaded-data');
  try {
    if (data) {
      const rawJson = JSON.parse(data);
      const parseJson = UploadedDataArray.parse(rawJson);
      return parseJson.length;
    } else {
      return 0;
    }
  } catch {
    return 0;
  }
  return 0;
}

export {
  addUploaded,
  getAllUploaded,
  getUploaded,
  getSize
};
