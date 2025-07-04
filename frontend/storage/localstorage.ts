import { useEffect, useState } from "react";
import * as z from "zod/v4-mini";

const UploadedData = z.object({
  fileName: z.string(),
  token: z.string(),
  url: z.string(),
});

const UploadedDataArray = z.array(UploadedData);

const keyStorage = 'uploaded-data';

type UploadedData = z.infer<typeof UploadedData>;

function useUploadedData() {
  const [fileCounter, setFileCounter] = useState(0);
  const [files, setFiles] = useState<UploadedData[]>([]);

  function saveOverrideToLocalStorage(backup: UploadedData[]) {
    localStorage.setItem(keyStorage, JSON.stringify(backup));
  }

  useEffect(() => {
    setFileCounter(getSize());
    setFiles(getAllUploaded());
  }, []);

  useEffect(() => {
    console.log('save to local storage');
    saveOverrideToLocalStorage(files);
  }, [files, fileCounter]);

  function addFile(fileName: string, xToken: string, url: string) {
    setFileCounter(c => c + 1);
    const data: UploadedData = {
      fileName,
      token: xToken,
      url
    };
    setFiles(f => [...f, data]);
  }

  function deleteFile(url: string) {
    setFileCounter(c => c - 1);
    setFiles(f =>
      f.filter(file => file.url !== url)
    );
  }

  const props = {
    counter: fileCounter,
    files,
    addFile,
    deleteFile,
  };

  return props;
}

function getAllUploaded() {
  try {
    const data = localStorage.getItem(keyStorage);
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

function getSize(): number {
  const data = localStorage.getItem(keyStorage);
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
}

export {
  useUploadedData,
};

export type {
  UploadedData,
};
